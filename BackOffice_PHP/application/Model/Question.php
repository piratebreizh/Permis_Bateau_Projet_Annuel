<?php

namespace APP\Model;

class Question extends \FSF\Model
{

    /**
     * @return string
     */
    public function getTableName()
    {
        return 'questions';
    }

    /**
     * @return string
     */
    public function getEntityClass()
    {
        return '\APP\Entity\Question';
    }

    /**
     * @return array
     */
    public function getPrimaryKey()
    {
        return 'id_question';
    }
}