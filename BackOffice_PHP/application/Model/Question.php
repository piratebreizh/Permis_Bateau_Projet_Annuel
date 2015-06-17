<?php

namespace APP\Model;

use FSF\Filter;
use FSF\Helper\Date;

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
     * @param int $id_examen
     * @return \FSF\EntityIterator
     */
    public function getQuestionsByIdExamen($id_examen)
    {
        $filters[]= new Filter('id_examen', $id_examen);

        return $this->findAllWithFilters($filters, 'numero_question');
    }

    /**
     * Get questions updated after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getUpdatedQuestions($date)
    {
        $filters[] = new Filter("date_modification", Date::frToUk($date), "date_modification", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("date_creation", Date::frToUk($date), "date_creation", Filter::OPERATOR_SMALLER);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }

    /**
     * Get questions created after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getNewQuestions($date)
    {
        $filters[] = new Filter("date_creation", Date::frToUk($date), "date_creation", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }
    /**
     * Get questions deleted after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getDeletedQuestions($date)
    {
        $filters[] = new Filter("date_modification", Date::frToUk($date), "date_modification", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("is_deleted", 1);

        return $this->findAllWithFilters($filters);
    }
}