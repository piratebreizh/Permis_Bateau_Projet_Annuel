<?php

namespace APP\Model;

use FSF\Filter;
use FSF\Helper\Date;
use FSF\Model;

class Cours extends Model
{

    /**
     * @return string
     */
    public function getTableName()
    {
        return "COURS";
    }

    /**
     * @return string
     */
    public function getEntityClass()
    {
        return '\APP\Entity\Cours';
    }

    /**
     * @return array
     */
    public function getPrimaryKey()
    {
        return array('id_cours');
    }

    /**
     * @param int $id_theme
     * @return \FSF\EntityIterator
     */
    public function getCoursByIdTheme($id_theme)
    {
        $filters[] = new Filter('id_theme', $id_theme);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }

    /**
     * Get cours created after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getNewCours($date)
    {
        $filters[] = new Filter("date_creation", Date::frToUk($date), "date_creation", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }
    /**
     * Get cours deleted that been created before the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getDeletedCours($date)
    {
        $filters[] = new Filter("date_creation", Date::frToUk($date), "date_creation", Filter::OPERATOR_SMALLER_OR_EQUAL);
        $filters[] = new Filter("date_modification", Date::frToUk($date), "date_modification", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("is_deleted", 1);

        return $this->findAllWithFilters($filters);
    }
}