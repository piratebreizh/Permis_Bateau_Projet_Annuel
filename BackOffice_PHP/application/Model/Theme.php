<?php

namespace APP\Model;

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
}