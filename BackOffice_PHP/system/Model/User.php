<?php

namespace FSF\Model;

use FSF\Filter;
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

    public function getUserByUserName($username)
    {
        $filters[] = new Filter('username', $username);

        return $this->findAllWithFilters($filters)->current();
    }

    public function getAll()
    {
        $filters[] = new Filter('is_deleted', 0);

        return $this->findAllWithFilters($filters);
    }
}