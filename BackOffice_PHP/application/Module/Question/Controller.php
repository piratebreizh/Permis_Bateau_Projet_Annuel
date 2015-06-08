<?php

namespace APP\Module\Question;

use APP\Entity\Examen;
use APP\Entity\Theme;
use APP\Model\Question as QuestionModel;
use APP\Entity\Question as Question;

use APP\Module\Question\View as ViewPath;

class Controller extends \FSF\Controller
{
    public function afficher()
    {
        $id = $this->getRequest()->get('id', 0);

        $model = new QuestionModel();
        /** @var \APP\Entity\Question $question */
        $question = $model->get($id);

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'affichage.phtml');
        $currentView->setParam("question", $question);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    public function creer()
    {
        $id_examen = $this->getRequest()->get("id_examen", "");

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'creation.phtml');
        $currentView->setParam("id_examen", $id_examen);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    public function saveQuestion()
    {
        $id_examen = $this->getRequest()->get("id_examen", "");
        $numero_question = $this->getRequest()->get("numero_question", "");
        $enonce_question = $this->getRequest()->get("question_enonce", "");
        $enonce_A = $this->getRequest()->get("enonce_A", "");
        $enonce_B = $this->getRequest()->get("enonce_B", "");
        $enonce_C = $this->getRequest()->get("enonce_C", "");
        $enonce_D = $this->getRequest()->get("enonce_D", "");
        $is_correct_A = $this->getRequest()->get("is_correct_A", false);
        $is_correct_B = $this->getRequest()->get("is_correct_B", false);
        $is_correct_C = $this->getRequest()->get("is_correct_C", false);
        $is_correct_D = $this->getRequest()->get("is_correct_D", false);

        $question = new Question();
        $question
            ->setIdExamen($id_examen)
            ->setNumeroQuestion($numero_question)
            ->setEnonceQuestion($enonce_question)
            ->setEnonceA($enonce_A)
            ->setEnonceB($enonce_B)
            ->setEnonceC($enonce_C)
            ->setEnonceD($enonce_D)
            ->setIsCorrectA($is_correct_A)
            ->setIsCorrectB($is_correct_B)
            ->setIsCorrectC($is_correct_C)
            ->setIsCorrectD($is_correct_D)
            ->save();

        header('Location: /examen/afficher?id=' . $id_examen);
    }
}