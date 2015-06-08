<?php

namespace APP\Model;

use FSF\Filter;
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

        return $this->findAllWithFilters($filters);
    }
}