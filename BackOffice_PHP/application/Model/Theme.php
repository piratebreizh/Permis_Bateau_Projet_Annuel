<?php

namespace APP\Model;

use FSF\Filter;
use FSF\Helper\Date;

class Theme extends \FSF\Model
{

    /**
     * @return string
     */
    public function getTableName()
    {
        return 'THEMES';
    }

    /**
     * @return string
     */
    public function getEntityClass()
    {
        return '\APP\Entity\Theme';
    }

    /**
     * @return array
     */
    public function getPrimaryKey()
    {
        return array('id_theme');
    }

    public function getThemes()
    {
        $builder = $this->select();

        return $this->findAll($builder);
    }

    /**
     * Get themes updated after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getUpdatedThemes($date)
    {
        $filters[] = new Filter("date_modification", Date::frToUk($date), "date_modification", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("date_creation", Date::frToUk($date), "date_creation", Filter::OPERATOR_SMALLER);
        $filters[] = new Filter("is_published", 1);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }

    /**
     * Get themes created after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getNewThemes($date)
    {
        $filters[] = new Filter("date_creation", Date::frToUk($date), "date_creation", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("is_published", 1);
        $filters[] = new Filter("is_deleted", 0);

        return $this->findAllWithFilters($filters);
    }
    /**
     * Get themes deleted after the given date
     * @param string $date
     * @return \FSF\EntityIterator
     */
    public function getDeletedThemes($date)
    {
        $filters[] = new Filter("date_modification", Date::frToUk($date), "date_modification", Filter::OPERATOR_GREATER_OR_EQUAL);
        $filters[] = new Filter("is_published", 1);
        $filters[] = new Filter("is_deleted", 1);

        return $this->findAllWithFilters($filters);
    }
}