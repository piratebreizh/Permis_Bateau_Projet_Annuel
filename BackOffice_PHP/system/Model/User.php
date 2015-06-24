<?php

namespace FSF\Model;

use FSF\Model;

class User extends Model
{

    /**
     * @return string
     */
    public function getTableName()
    {
        return 'USERS';
    }

    /**
     * @return string
     */
    public function getEntityClass()
    {
        return '\FSF\Entity\User';
    }

    /**
     * @return array
     */
    public function getPrimaryKey()
    {
        return array('id_user');
    }
}