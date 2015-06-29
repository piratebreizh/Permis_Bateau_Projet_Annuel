<?php

namespace APP\Model;

use FSF\Filter;
use FSF\Helper\Date;

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

    /**
     * @param int $id_theme
     * @return \FSF\EntityIterator
     */
    public function getExamensByIdTheme($id_theme)
    {
        $filters[] = new Filter('id_theme', $id_theme);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters, 'numero');
    }

    /**
     * @return \FSF\EntityIterator
     */
    public function getExamensBlancs()
    {
        $filters[] = new Filter('id_theme', 0);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }

    /**
     * Get examens updated after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getUpdatedExamens($date)
    {
        $filters[] = new Filter("date_modification", Date::frToUk($date), "date_modification", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("date_creation", Date::frToUk($date), "date_creation", Filter::OPERATOR_SMALLER);
        $filters[] = new Filter("is_published", 1);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }

    /**
     * Get examens created after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getNewExamens($date)
    {
        $filters[] = new Filter("date_creation", Date::frToUk($date), "date_creation", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("is_published", 1);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }
    /**
     * Get examens deleted after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getDeletedExamens($date)
    {
        $filters[] = new Filter("date_modification", Date::frToUk($date), "date_modification", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("is_published", 1);
        $filters[] = new Filter("is_deleted", 1);

        return $this->findAllWithFilters($filters);
    }

    public function updateNumerotation($id_theme, $numero)
    {
        $filters[]= new Filter('id_theme', $id_theme);
        $filters[]= new Filter('numero', $numero, 'numero_question', Filter::OPERATOR_GREATER);
        $filters[] = new Filter("is_deleted", 0);

        $examens = $this->findAllWithFilters($filters);

        foreach($examens as $examen){
            /** @var \APP\Entity\Examen $examen */
            $examen->setNumero($examen->getNumero()-1);
            $examen->save();
        }
    }
}