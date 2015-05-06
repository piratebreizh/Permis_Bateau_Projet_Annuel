<?php

namespace APP\Model;

class Examen extends \FSF\Model
{

    /**
     * @return string
     */
    public function getTableName()
    {
        return 'EXAMENS';
    }

    /**
     * @return string
     */
    public function getEntityClass()
    {
        return '\APP\Entity\Examen';
    }

    /**
     * @return array
     */
    public function getPrimaryKey()
    {
        return array('id_examen');
    }
}