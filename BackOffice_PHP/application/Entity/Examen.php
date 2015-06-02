<?php

namespace APP\Entity;

class Examen extends \FSF\Entity
{

    protected $cols = array(
        'id_examen'         => null,
        'nom'               => null,
        'numero'            => null,
        'id_theme'          => null,
        'date_modification' => null,
        'is_published'      => false,
    );

    protected $types = array(
        'id_examen'         => \PDO::PARAM_INT,
        'nom'               => \PDO::PARAM_STR,
        'numero'            => \PDO::PARAM_INT,
        'id_theme'          => \PDO::PARAM_INT,
        'date_modification' => \PDO::PARAM_STR,
        'is_published'      => \PDO::PARAM_INT,
    );

    public function __construct()
    {
        $this->setModel(new \APP\Model\Examen());
    }

    /**
     * @return int
     */
    public function getIdExamen()
    {
        return (int)$this->cols['id_examen'];
    }

    /**
     * @param int $id_examen
     * @return \APP\Entity\Examen
     */
    public function setIdExamen($id_examen)
    {
        $this->cols['id_examen'] = (int)$id_examen;

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
     * @return \APP\Entity\Examen
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
     * @return \APP\Entity\Examen
     */
    public function setNumero($numero)
    {
        $this->cols['numero'] = (int)$numero;

        return $this;
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
     * @return \APP\Entity\Examen
     */
    public function setIdTheme($id_theme)
    {
        $this->cols['id_theme'] = (int)$id_theme;

        return $this;
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
     * @return int
     */
    public function save()
    {
        $this->cols['date_modification'] = date('Y-m-d H:i:s');

        return parent::save();
    }

}