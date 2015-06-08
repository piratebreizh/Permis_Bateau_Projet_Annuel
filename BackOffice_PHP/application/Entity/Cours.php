<?php

namespace APP\Entity;

use FSF\Entity;

class Cours extends Entity
{
    protected $cols = array(
        'id_cours'      => null,
        'nom'           => null,
        'nom_pdf'       => null,
        'id_theme'      => null,
        'date_creation' => null,
        'is_deleted'    => false,
    );

    protected $types = array(
        'id_cours'      => \PDO::PARAM_INT,
        'nom'           => \PDO::PARAM_STR,
        'nom_pdf'       => \PDO::PARAM_STR,
        'id_theme'      => \PDO::PARAM_INT,
        'date_creation' => \PDO::PARAM_STR,
        'is_deleted'    => \PDO::PARAM_INT,
    );

    public function __construct()
    {
        $this->setModel(new \APP\Model\Cours());
        $this->cols['date_creation'] = date('Y-m-d H:i:s');
    }

    /**
     * @return int
     */
    public function getIdCours()
    {
        return (int)$this->cols['id_cours'];
    }

    /**
     * @param int $id_cours
     * @return \APP\Entity\Cours
     */
    public function setIdCours($id_cours)
    {
        $this->cols['id_cours'] = (int)$id_cours;

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
     * @return \APP\Entity\Cours
     */
    public function setNom($nom)
    {
        $this->cols['nom'] = (string)$nom;

        return $this;
    }

    /**
     * @return string
     */
    public function getNomPdf()
    {
        return (string)$this->cols['nom_pdf'];
    }

    /**
     * @param string $nom_pdf
     * @return \APP\Entity\Cours
     */
    public function setNomPdf($nom_pdf)
    {
        $this->cols['nom_pdf'] = (string)$nom_pdf;

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
     * @return \APP\Entity\Cours
     */
    public function setIdTheme($id_theme)
    {
        $this->cols['id_theme'] = (int)$id_theme;

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
     * @return bool
     */
    public function isDeleted()
    {
        return (bool)$this->cols['is_deleted'];
    }

    /**
     * @param bool $is_deleted
     * @return \APP\Entity\Cours
     */
    public function setIsDeleted($is_deleted)
    {
        $this->cols['is_deleted'] = $is_deleted ? 1 : 0;

        return $this;
    }


}