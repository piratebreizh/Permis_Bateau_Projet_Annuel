<?php

namespace FSF;

use Doctrine\DBAL\Connection;
use Doctrine\DBAL\Query\QueryBuilder;

abstract class Model
{

    /** @var  Connection */
    private $database;

    /** @var string */
    protected $entityIteratorClass = 'FSF\EntityIterator';

    /** @var  Session */
    private $session;

    /**
     * @return string
     */
    public abstract function getTableName();

    /**
     * @return string
     */
    public abstract function getEntityClass();

    /**
     * @return array
     */
    public abstract function getPrimaryKey();

    /**
     * @return bool
     */
    public function isAutoincrement()
    {
        return true;
    }

    /**
     * @return Session
     */
    public function getSession()
    {
        if (!$this->session) {
            $this->session = Session::getInstance();
        }
        return $this->session;
    }

    /**
     * @param Session $session
     * @return $this
     */
    public function setSession(Session $session)
    {
        $this->session = $session;
        return $this;
    }


    /**
     * @return \Doctrine\DBAL\Connection
     */
    public function getDb()
    {
        if (is_null($this->database)) {
            $this->database = Helper\Db::getInstance();
        }
        return $this->database;
    }

    /**
     * @param Connection $connection
     * @return $this
     */
    public function setDb(Connection $connection)
    {
        $this->database = $connection;
        return $this;
    }

    /**
     * Define the entity iterator class
     * @param string $entityIteratorClass
     * @return $this
     * @throws Exception
     */
    public function setEntityIteratorClass($entityIteratorClass)
    {
        if (!class_exists($entityIteratorClass)) {
            throw new Exception('Unable to create Entity Iterator');
        }
        $myReflection = new \ReflectionClass($entityIteratorClass);
        if (!$myReflection->isSubclassOf('\FSF\EntityIterator')) {
            throw new Exception('Design error - Entity Iterator must be instance of \FSF\EntityIterator');
        }
        $this->entityIteratorClass = $entityIteratorClass;
        return $this;
    }

    /**
     * @return string
     */
    public function getEntityIteratorClass()
    {
        return $this->entityIteratorClass;
    }

    /**
     * Return Entity by its id
     * @param array|int $id
     * @return Entity
     */
    public function get($id)
    {
        $arrayId = (array)$id;
        $isUniqueKey = (count($arrayId) == 1);
        $select = $this->select('*');
        foreach ((array)$this->getPrimaryKey() as $key) {
            $select->andWhere($key . ' = :' . $key)
                ->setParameter(
                    $key,
                    array_key_exists($key, $arrayId)
                        ? $arrayId[$key]
                        : ($isUniqueKey ? $arrayId[0] : null)
                );
        }
        return $this->findOne($select);
    }

    /**
     * Persists entity in database
     * @param Entity $entity
     * @param bool $fullUpdate If needed to persist the object fully (saves null values)
     * @return \Doctrine\DBAL\Driver\Statement|int
     */
    public function save(Entity $entity, $fullUpdate = false)
    {
        $cols = $entity->toArray();
        $types = $entity->getTypes();
        $queryBuilder = $this->getDb()->createQueryBuilder();
        $isUpdate = true;
        //if primary key is null, it is an Update
        foreach ((array)$this->getPrimaryKey() as $key) {
            $isUpdate = $isUpdate && !is_null($cols[$key]);
        }
        if ($isUpdate) {
            $query = $queryBuilder->update($this->getTableName());
            foreach ((array)$this->getPrimaryKey() as $key) {
                $type = (isset($types[$key])) ? $types[$key] : null;
                $value = (isset($cols[$key])) ? $cols[$key] : null;
                $query->andWhere($key . ' = :' . $key)
                    ->setParameter($key, $value, $type);
            }
            foreach ($cols as $key => $value) {
                if (
                    !in_array($key, (array)$this->getPrimaryKey())
                    && ( ($fullUpdate == true && $value == null) || is_null($value) == false )
                ) {
                    $type = (isset($types[$key])) ? $types[$key] : null;
                    $query->set($key, ':' . $key)
                        ->setParameter($key, $value, $type);
                }
            }
        } else {
            $query = $queryBuilder->insert($this->getTableName());
            foreach ($cols as $key => $value) {
                if (!in_array($key, (array)$this->getPrimaryKey()) || !$this->isAutoincrement()) {
                    $type = (isset($types[$key])) ? $types[$key] : null;
                    $query->setValue($key, ':' . $key)
                        ->setParameter($key, $value, $type);
                }
            }
        }

        $return = $query->execute();
        if (!$isUpdate && $this->isAutoincrement()) {
            $entity->setFromArray(
                array_fill_keys((array)$this->getPrimaryKey(), (int)$this->getDb()->lastInsertId())
            );
        }
        return $return;
    }

    /**
     * @param string $fields
     * @return QueryBuilder
     */
    protected function select($fields = '*')
    {
        return $this->getDb()
            ->createQueryBuilder()
            ->select($fields)
            ->from($this->getTableName());
    }

    /**
     * @param QueryBuilder $builder
     * @return EntityIterator
     * @throws Exception
     */
    protected function findAll(QueryBuilder $builder)
    {
        /* @var $iterator EntityIterator */
        $iterator = new $this->entityIteratorClass();
        $iterator->append(new \ArrayIterator($builder->execute()->fetchAll()));
        return $iterator->setEntityClass($this->getEntityClass())->setModel($this);
    }

    /**
     * @param QueryBuilder $builder
     * @return Entity
     * @throws Exception
     */
    protected function findOne(QueryBuilder $builder)
    {
        $result = $builder->execute()->fetch();
        if (!$result) return null;
        /* @var $iterator EntityIterator */
        $iterator = new $this->entityIteratorClass();
        $iterator->append(new \ArrayIterator(array($result)));
        $iterator->setEntityClass($this->getEntityClass())->setModel($this);
        $iterator->rewind();
        return $iterator->current();
    }

    /**
     * @param Filter[] $filters
     * @return EntityIterator
     */
    protected function findAllWithFilters($filters)
    {
        $builder = $this->select();

        foreach($filters as $filter){
            $filter->applyOnQueryBuilder($builder);
        }

        return $this->findAll($builder);
    }
}
