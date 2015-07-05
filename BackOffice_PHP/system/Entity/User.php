<?php

namespace FSF\Entity;

use FSF\Entity;

class User extends Entity
{

    protected $cols = array(
        'id_user'    => null,
        'name'       => null,
        'firstname'  => null,
        'username'   => null,
        'password'   => null,
        'is_deleted' => false,
    );

    protected $types = array(
        'id_user'    => \PDO::PARAM_INT,
        'name'       => \PDO::PARAM_STR,
        'firstname'  => \PDO::PARAM_STR,
        'username'   => \PDO::PARAM_STR,
        'password'   => \PDO::PARAM_STR,
        'is_deleted' => \PDO::PARAM_INT,
    );

    public function __construct()
    {
        $this->setModel(new \FSF\Model\User());
    }

    /**
     * @return int
     */
    public function getIdUser()
    {
        return (int)$this->cols['id_user'];
    }

    /**
     * @param int $id_user
     * @return User
     */
    public function setIdUser($id_user)
    {
        $this->cols['id_user'] = (int)$id_user;

        return $this;
    }

    /**
     * @return string
     */
    public function getName()
    {
        return (string)$this->cols['name'];
    }

    /**
     * @param string $name
     * @return User
     */
    public function setName($name)
    {
        $this->cols['name'] = (string)$name;

        return $this;
    }

    /**
     * @return string
     */
    public function getFirstName()
    {
        return (string)$this->cols['firstname'];
    }

    /**
     * @param string $firstname
     * @return User
     */
    public function setFirstName($firstname)
    {
        $this->cols['firstname'] = (string)$firstname;

        return $this;
    }

    /**
     * @return string
     */
    public function getUserName()
    {
        return (string)$this->cols['username'];
    }

    /**
     * @param string $username
     * @return User
     */
    public function setUserName($username)
    {
        $this->cols['username'] = (string)$username;

        return $this;
    }

    /**
     * @return string
     */
    public function getPassword()
    {
        return (string)$this->cols['password'];
    }

    /**
     * @param string $password
     * @return User
     */
    public function setPassword($password)
    {
        $cost = 10;
        $salt = strtr(base64_encode(mcrypt_create_iv(16, MCRYPT_DEV_URANDOM)), '+', '.');
        $salt = sprintf("$2a$%02d$", $cost).$salt;
        $hash = crypt($password, $salt);

        $this->cols['password'] = (string)$hash;

        return $this;
    }

    /**
     * @return bool
     */
    public function isDeleted()
    {
        return (bool)$this->cols['is_deleted'];
    }

    /**
     * @param bool $is_deleted
     * @return \APP\Entity\Theme
     */
    public function setisDeleted($is_deleted)
    {
        $this->cols['is_deleted'] = $is_deleted ? 1 : 0;

        return $this;
    }

    /**
     * @return string
     */
    public function getLabel()
    {
        return $this->getFirstName().' '.$this->getName();
    }
}