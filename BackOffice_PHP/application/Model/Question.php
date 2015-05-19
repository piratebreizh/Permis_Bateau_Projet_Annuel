<?php

namespace APP\Model;

use FSF\Filter;

class Question extends \FSF\Model
{

    /**
     * @return string
     */
    public function getTableName()
    {
        return 'QUESTIONS';
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
        return array('id_question');
    }

    /**
     * @return \FSF\EntityIterator
     */
    public function getAllQuestions()
    {
        $builder = $this->select();

        return $this->findAll($builder);
    }

    /**
     * @param int $id_examen
     * @return \FSF\EntityIterator
     */
    public function getQuestionsByIdExamen($id_examen)
    {
        $filters[]= new Filter('id_examen', $id_examen);

        return $this->findAllWithFilters($filters);
    }
}