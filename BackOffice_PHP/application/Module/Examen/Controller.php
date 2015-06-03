<?php

namespace APP\Module\Examen;

use APP\Entity\Examen;
use APP\Model\Question;
use APP\Module\Examen\View as ViewPath;

class Controller extends \FSF\Controller
{
    function afficher()
    {
        $id_examen = $this->getRequest()->get('id');

        $examenModel = new \APP\Model\Examen();
        $examen = $examenModel->get($id_examen);

        $question_model = new Question();
        $questions = $question_model->getQuestionsByIdExamen($id_examen);

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'affichage.phtml');
        $currentView->setParam("examen", $examen);
        $currentView->setParam("questions", $questions);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    function saveExamen()
    {
        $numero_examen = $this->getRequest()->get("numero_examen", "");
        $id_theme = $this->getRequest()->get("id_theme", ""); //TODO Default value == THEME Examen Blanc ?
        $nom = $this->getRequest()->get("nom", "");

        $examen = new Examen();
        $examen
            ->setNumero($numero_examen)
            ->setIdTheme($id_theme)
            ->setNom($nom)
            ->save();

        if ($id_theme > 0) {
            return '<a href="/theme/afficher?id=' . $id_theme . '" >Retour au thème</a>';
        } else {
            return '<a href="/theme/listerExamensBlanc" >Retour à la liste des examens blancs</a>';
        }
    }

    public function creer()
    {
        $id_theme = $this->getRequest()->get("id_theme", "");

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'creation.phtml');
        $currentView->setParam("id_theme", $id_theme);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }
}