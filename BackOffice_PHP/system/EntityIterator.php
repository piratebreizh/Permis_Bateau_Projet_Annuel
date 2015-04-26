<?php

namespace FSF;

class EntityIterator extends \AppendIterator implements \Countable
{
    /** @var  string */
    private $entityClass;
    /** @var  Model */
    private $model;

    /**
     * Class to use as entity
     * @param string $className
     * @return $this
     * @throws \LogicException
     */
    public function setEntityClass($className)
    {
        if (!class_exists($className)) {
            throw new \LogicException("Design error - Entity class seems not to exist : " . $className);
        }
        $this->entityClass = $className;
        return $this;
    }

    /**
     * Get class to use as entity
     * @return string
     */
    public function getEntityClass()
    {
        return $this->entityClass;
    }

    /**
     * Gets model instance that will be bind to entity
     * @return Model
     */
    public function getModel()
    {
        return $this->model;
    }

    /**
     * Define the model to bind to entity
     * @param Model $model
     * @return $this
     */
    public function setModel(Model $model)
    {
        $this->model = $model;
        return $this;
    }

    /**
     * Convert current cursor entity array to an entity
     * @return Entity
     * @throws \LogicException
     */
    public function current()
    {
        if (!$this->valid()) return null;
        /** @var Entity $entity */
        $entity = new $this->entityClass();
        if (!$entity instanceof Entity) {
            throw new \LogicException("Design error - Entity class must be instance of FSF\\Entity");
        }
        return $entity->setModel($this->getModel())->setFromArray(parent::current());
    }

    /**
     * Return the number of elements
     * @return int
     */
    public function count()
    {
        return iterator_count($this);
    }
}
