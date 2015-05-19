<?php

namespace APP\Model;

use FSF\Filter;

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

        return $this->findAllWithFilters($filters);
    }

    /**
     * @return \FSF\EntityIterator
     */
    public function getExamensBlancs()
    {
        $filters[] = new Filter('id_theme', 0);

        return $this->findAllWithFilters($filters);
    }
}