<?php

namespace APP\Module\Question;

class Controller extends \FSF\Controller
{
    public function afficher()
    {
        $id = $this->getRequest()->get('id',0);
        echo 'afficher Question : '.$id;
    }
}