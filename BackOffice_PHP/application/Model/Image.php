<?php

namespace APP\Model;

use FSF\Model;

class Image extends Model
{

    /**
     * @return string
     */
    public function getTableName()
    {
        return "IMAGES";
    }

    /**
     * @return string
     */
    public function getEntityClass()
    {
        return '\APP\Entity\Image';
    }

    /**
     * @return array
     */
    public function getPrimaryKey()
    {
        return array('id_image');
    }
}