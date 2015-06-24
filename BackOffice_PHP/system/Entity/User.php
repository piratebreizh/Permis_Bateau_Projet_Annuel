<?php

namespace FSF\Entity;

use FSF\Entity;

class User extends Entity
{

    protected $cols = array(
        'id_user'  => null,
        'nom'      => null,
        'prenom'   => null,
        'login'    => null,
        'password' => null,
    );

    protected $types = array(
        'id_user'  => \PDO::PARAM_INT,
        'nom'      => \PDO::PARAM_STR,
        'prenom'   => \PDO::PARAM_STR,
        'login'    => \PDO::PARAM_STR,
        'password' => \PDO::PARAM_STR,
    );

    /**
     * @return int
     */
    public function getIdUser()
    {
        return (int)$this->cols['id_user'];
    }

    /**
     * @param int $id_user
     */
    public function setIdUser($id_user)
    {
        $this->cols['id_user'] = (int)$id_user;
    }

    /**
     * @return string
     */
    public function getNom()
    {
        return (string)$this->cols['nom'];
    }

    /**
     * @param string $nom
     */
    public function setNom($nom)
    {
        $this->cols['nom'] = (string)$nom;
    }

    /**
     * @return string
     */
    public function getPrenom()
    {
        return (string)$this->cols['prenom'];
    }

    /**
     * @param string $prenom
     */
    public function setPrenom($prenom)
    {
        $this->cols['prenom'] = (string)$prenom;
    }

    /**
     * @return string
     */
    public function getLogin()
    {
        return (string)$this->cols['login'];
    }

    /**
     * @param string $login
     */
    public function setLogin($login)
    {
        $this->cols['login'] = (string)$login;
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
     */
    public function setPassword($password)
    {
        $this->cols['password'] = (string)$password;
    }


}