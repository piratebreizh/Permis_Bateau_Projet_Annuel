<?php

namespace FSF;

abstract class Entity
{

    /** @var  Model */
    protected $model;
    /**
     * Structure of the identity indexed with field as key and default values as values
     * @var array
     */
    protected $cols = array();

    /**
     * Structure of the type of the identity indexed with field as key and type as values
     * @var array
     */
    protected $types = array();

    /**
     * Get dependent model
     * @return Model
     * @throws Exception
     */
    public function getModel()
    {
        if (!$this->model) {
            throw new Exception('Model is not bind');
        }
        return $this->model;
    }

    /**
     * Define the model bind to the entity
     * @param Model $model
     * @return $this
     */
    public function setModel(Model $model)
    {
        $this->model = $model;
        return $this;
    }

    /**
     * Redefine all values to the entity
     * @param array $params
     * @return $this
     */
    public function setFromArray($params = array())
    {
        foreach ($params as $key => $value) {
            if (array_key_exists($key, $this->cols)) {
                $this->cols[$key] = $value;
            }
        }
        return $this;
    }

    /**
     * Transform the entity as an indexed array
     * @return array
     */
    public function toArray()
    {
        return $this->cols;
    }

    /**
     * @return array
     */
    public function getTypes()
    {
        return $this->types;
    }

    /**
     * Persists entity in database by its model
     * @return int
     */
    public function save()
    {
        return $this->getModel()->save($this);
    }
}