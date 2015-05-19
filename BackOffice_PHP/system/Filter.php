<?php

namespace FSF;

use Doctrine\DBAL\Query\QueryBuilder;

class Filter
{
    protected $field_name;
    protected $parameter_name;
    protected $operator;
    protected $value;

    const OPERATOR_EQUAL = 1;
    const OPERATOR_NOT_EQUAL = 2;
    const OPERATOR_CONTAIN = 3;
    const OPERATOR_SMALLER = 4;
    const OPERATOR_GREATER = 5;
    const OPERATOR_SMALLER_OR_EQUAL = 6;
    const OPERATOR_GREATER_OR_EQUAL = 7;

    private static $OPERATOR_EQUIVALENCE = array(
        self::OPERATOR_EQUAL     => "@ = :#",
        self::OPERATOR_NOT_EQUAL => "@ <> :#",
        self::OPERATOR_CONTAIN   => "@ LIKE %:#%",
    );

    /**
     * @param string $field_name
     * @param mixed $value
     * @param string $parameter_name
     * @param int $operator
     */
    public function __construct($field_name, $value, $parameter_name = '', $operator = self::OPERATOR_EQUAL)
    {
        $this->field_name = $field_name;
        $this->value = $value;
        $this->parameter_name = $parameter_name != '' ? $parameter_name : $field_name;
        $this->operator = $operator;
    }

    /**
     * @param QueryBuilder $builder
     */
    private function setAndWhere(QueryBuilder &$builder)
    {
        $where = self::$OPERATOR_EQUIVALENCE[$this->operator];
        $where = str_replace('@', $this->field_name, $where);
        $where = str_replace('#', $this->parameter_name, $where);

        $builder->andWhere($where);
    }

    /**
     * @param QueryBuilder $builder
     */
    private function setParameter(QueryBuilder &$builder)
    {
        $builder->setParameter($this->parameter_name, $this->value);
    }

    /**
     * @param QueryBuilder $builder
     */
    public function applyOnQueryBuilder(QueryBuilder &$builder)
    {
        $this->setAndWhere($builder);
        $this->setParameter($builder);
    }
}