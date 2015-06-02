<?php

namespace APP\Entity;

class Theme extends \FSF\Entity
{
    protected $cols = array(
        'id_theme'          => null,
        'nom'               => null,
        'numero'            => null,
        'date_creation'     => null,
        'date_modification' => null,
        'is_published'      => false,
        'is_deleted'        => false,
    );

    protected $types = array(
        'id_theme'          => \PDO::PARAM_INT,
        'nom'               => \PDO::PARAM_STR,
        'numero'            => \PDO::PARAM_INT,
        'date_creation'     => \PDO::PARAM_STR,
        'date_modification' => \PDO::PARAM_STR,
        'is_published'      => \PDO::PARAM_INT,
        'is_deleted'        => \PDO::PARAM_INT,
    );

    public function __construct()
    {
        $this->setModel(new \APP\Model\Theme());
        $this->cols['date_creation'] = date('Y-m-d H:i:s');
    }

    /**
     * @return int
     */
    public function getIdTheme()
    {
        return (int)$this->cols['id_theme'];
    }

    /**
     * @param int $id_theme
     * @return \APP\Entity\Theme
     */
    public function setIdTheme($id_theme)
    {
        $this->cols['id_theme'] = (int)$id_theme;

        return $this;
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
     * @return \APP\Entity\Theme
     */
    public function setNom($nom)
    {
        $this->cols['nom'] = (string)$nom;

        return $this;
    }

    /**
     * @return int
     */
    public function getNumero()
    {
        return (int)$this->cols['numero'];
    }

    /**
     * @param int $numero
     * @return \APP\Entity\Theme
     */
    public function setNumero($numero)
    {
        $this->cols['numero'] = (int)$numero;

        return $this;
    }

    /**
     * @return string Date in FR format (dd/mm/yyyy)
     */
    public function getDateCreation()
    {
        return \FSF\Helper\Date::ukToFr($this->cols['date_creation']);
    }

    /**
     * @return string Date in FR format (dd/mm/yyyy)
     */
    public function getDateModification()
    {
        return \FSF\Helper\Date::ukToFr($this->cols['date_modification']);
    }

    /**
     * @return bool
     */
    public function isPublished()
    {
        return (bool) $this->cols['is_published'];
    }

    /**
     * @param bool $is_published
     * @return \APP\Entity\Theme
     */
    public function setIsPublished($is_published)
    {
        $this->cols['is_published'] = $is_published ? 1 : 0;

        return $this;
    }

    /**
     * @return bool
     */
    public function isDeleted()
    {
        return (bool) $this->cols['is_deleted'];
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
     * @return int
     */
    public function save()
    {
        $this->cols['date_modification'] = date('Y-m-d H:i:s');

        return parent::save();
    }

}