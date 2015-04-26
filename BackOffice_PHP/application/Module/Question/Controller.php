<?php

namespace APP\Module\Question;

class Controller extends \FSF\Controller
{
    public function afficher()
    {
        $id = $this->getRequest()->get('id',0);
        echo 'afficher Question : '.$id;

        $model = new \APP\Model\Question();
        $question = $model->get($id);

        var_dump($question->cols);
    }
}