<?php

namespace APP\Entity;

class Question extends \FSF\Entity
{
    protected $cols = array(
        'id_question'       => null,
        'numero_question'   => 0,
        'enonce_question'   => '',
        'is_correct_A'      => 0,
        'is_correct_B'      => 0,
        'is_correct_C'      => 0,
        'is_correct_D'      => 0,
        'enonce_A'          => '',
        'enonce_B'          => '',
        'enonce_C'          => '',
        'enonce_D'          => '',
        'id_image'          => '',
        'id_examen'         => '',
        'date_creation'     => null,
        'date_modification' => '',
        'is_deleted'        => false,
    );

    protected $type = array(
        'id_question'       => \PDO::PARAM_INT,
        'numero_question'   => \PDO::PARAM_INT,
        'enonce_question'   => \PDO::PARAM_STR,
        'is_correct_A'      => \PDO::PARAM_BOOL,
        'is_correct_B'      => \PDO::PARAM_BOOL,
        'is_correct_C'      => \PDO::PARAM_BOOL,
        'is_correct_D'      => \PDO::PARAM_BOOL,
        'enonce_A'          => \PDO::PARAM_STR,
        'enonce_B'          => \PDO::PARAM_STR,
        'enonce_C'          => \PDO::PARAM_STR,
        'enonce_D'          => \PDO::PARAM_STR,
        'id_image'          => \PDO::PARAM_INT,
        'id_examen'         => \PDO::PARAM_INT,
        'date_creation'     => \PDO::PARAM_STR,
        'date_modification' => \PDO::PARAM_STR,
        'is_deleted'        => \PDO::PARAM_INT,
    );

    public function __construct()
    {
        $this->setModel(new \APP\Model\Question());
        $this->cols['date_creation'] = date('Y-m-d H:i:s');
    }

    /************
     *  Getters
     ************/

    /**
     * @return int
     */
    public function getId()
    {
        return (int)$this->cols['id_question'];
    }

    /**
     * @return int
     */
    public function getNumeroQuestion()
    {
        return (int)$this->cols['numero_question'];
    }

    /**
     * @return string
     */
    public function getEnonceQuestion()
    {
        return (string)$this->cols['enonce_question'];
    }

    /**
     * @return bool
     */
    public function IsCorrectA()
    {
        return $this->cols['is_correct_A'] == 1;
    }

    /**
     * @return bool
     */
    public function IsCorrectB()
    {
        return $this->cols['is_correct_B'] == 1;
    }

    /**
     * @return bool
     */
    public function IsCorrectC()
    {
        return $this->cols['is_correct_C'] == 1;
    }

    /**
     * @return bool
     */
    public function IsCorrectD()
    {
        return $this->cols['is_correct_D'] == 1;
    }

    /**
     * @return string
     */
    public function getEnonceA()
    {
        return (string)$this->cols['enonce_A'];
    }

    /**
     * @return string
     */
    public function getEnonceB()
    {
        return (string)$this->cols['enonce_B'];
    }

    /**
     * @return string
     */
    public function getEnonceC()
    {
        return (string)$this->cols['enonce_C'];
    }

    /**
     * @return string
     */
    public function getEnonceD()
    {
        return (string)$this->cols['enonce_D'];
    }

    /**
     * @return int
     */
    public function getIdImage()
    {
        return (int)$this->cols['id_image'];
    }

    /**
     * @return int
     */
    public function getIdExamen()
    {
        return (int)$this->cols['id_examen'];
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

    /************
     *  Setters
     ************/

    /**
     * @param int $id
     * @return \APP\Entity\Question
     */
    public function setId($id)
    {
        $this->cols['id_question'] = (int)$id;

        return $this;
    }

    /**
     * @param int $numero_question
     * @return \APP\Entity\Question
     */
    public function setNumeroQuestion($numero_question)
    {
        $this->cols['numero_question'] = (int)$numero_question;

        return $this;
    }

    /**
     * @param string $enonce_question
     * @return \APP\Entity\Question
     */
    public function setEnonceQuestion($enonce_question)
    {
        $this->cols['enonce_question'] = (string)$enonce_question;

        return $this;
    }

    /**
     * @param bool $is_correct_A
     * @return \APP\Entity\Question
     */
    public function setIsCorrectA($is_correct_A)
    {
        $this->cols['is_correct_A'] = $is_correct_A ? 1 : 0;

        return $this;
    }

    /**
     * @param bool $is_correct_B
     * @return \APP\Entity\Question
     */
    public function setIsCorrectB($is_correct_B)
    {
        $this->cols['is_correct_B'] = $is_correct_B ? 1 : 0;

        return $this;
    }

    /**
     * @param bool $is_correct_C
     * @return \APP\Entity\Question
     */
    public function setIsCorrectC($is_correct_C)
    {
        $this->cols['is_correct_C'] = $is_correct_C ? 1 : 0;

        return $this;
    }

    /**
     * @param bool $is_correct_D
     * @return \APP\Entity\Question
     */
    public function setIsCorrectD($is_correct_D)
    {
        $this->cols['is_correct_D'] = $is_correct_D ? 1 : 0;

        return $this;
    }

    /**
     * @param string $enonce_A
     * @return \APP\Entity\Question
     */
    public function setEnonceA($enonce_A)
    {
        $this->cols['enonce_A'] = (string)$enonce_A;

        return $this;
    }

    /**
     * @param string $enonce_B
     * @return \APP\Entity\Question
     */
    public function setEnonceB($enonce_B)
    {
        $this->cols['enonce_B'] = (string)$enonce_B;

        return $this;
    }

    /**
     * @param string $enonce_C
     * @return \APP\Entity\Question
     */
    public function setEnonceC($enonce_C)
    {
        $this->cols['enonce_C'] = (string)$enonce_C;

        return $this;
    }

    /**
     * @param string $enonce_D
     * @return \APP\Entity\Question
     */
    public function setEnonceD($enonce_D)
    {
        $this->cols['enonce_D'] = (string)$enonce_D;

        return $this;
    }

    /**
     * @param int $id_image
     * @return \APP\Entity\Question
     */
    public function setIdImage($id_image)
    {
        $this->cols['id_image'] = (int)$id_image;

        return $this;
    }

    /**
     * @param int $id_examen
     * @return \APP\Entity\Question
     */
    public function setIdExamen($id_examen)
    {
        $this->cols['id_examen'] = (int)$id_examen;

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