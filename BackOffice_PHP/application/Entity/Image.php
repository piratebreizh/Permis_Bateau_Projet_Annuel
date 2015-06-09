<?php

namespace APP\Entity;

use FSF\Entity;

class Image extends Entity
{
    protected $cols = array(
        'id_image'  => null,
        'nom_image' => null,
    );

    protected $types = array(
        'id_image'  => \PDO::PARAM_INT,
        'nom_image' => \PDO::PARAM_STR,
    );

    public function __construct()
    {
        $this->setModel(new \APP\Model\Image());
    }



    /**
     * @return int
     */
    public function getIdImage()
    {
        return (int)$this->cols['id_image'];
    }

    /**
     * @param int $id_image
     * @return \APP\Entity\Image
     */
    public function setIdImage($id_image)
    {
        $this->cols['id_image'] = (int)$id_image;

        return $this;
    }

    /**
     * @return string
     */
    public function getNomImage()
    {
        return (string)$this->cols['nom_image'];
    }

    /**
     * @param string $nom_image
     * @return \APP\Entity\Image
     */
    public function setNomImage($nom_image)
    {
        $nom_image = uniqid().'_'.$nom_image;
        $nom_image = substr($nom_image, 0, 50);

        $this->cols['nom_image'] = (string)$nom_image;

        return $this;
    }
}