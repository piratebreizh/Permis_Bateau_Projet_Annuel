<?php

namespace APP\Entity;

class Question extends \FSF\Entity
{
    //TODO change to protected
    public $cols = array(
        'id_question' => null,
        'numero_question' => null,
        'enonce_question' => null,
        'is_correct_A' => null,
        'is_correct_B' => null,
        'is_correct_C' => null,
        'is_correct_D' => null,
        'enonce_A' => null,
        'enonce_B' => null,
        'enonce_C' => null,
        'enonce_D' => null,
    );

    public function getId()
    {
        return $this->cols['id_question'];
    }
}