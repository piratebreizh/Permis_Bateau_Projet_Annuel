<?php

namespace APP\Entity;

class Theme extends \FSF\Entity
{
    protected $cols = array(
        'id_theme' => null,
        'nom'      => null,
        'numero'   => null,
    );

    protected $types = array(
        'id_theme' => \PDO::PARAM_INT,
        'nom'      => \PDO::PARAM_STR,
        'numero'   => \PDO::PARAM_INT,
    );

    public function __construct()
    {
        $this->setModel(new \APP\Model\Theme());
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


}