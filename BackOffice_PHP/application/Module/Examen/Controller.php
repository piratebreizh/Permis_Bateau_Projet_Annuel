<?php

namespace APP\Module\Examen;

use APP\Entity\Examen;
use APP\Entity\Theme;
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
            ->setParam('currentView', $currentView)
            ->setParam("js", array("examen/affichage"));
    }

    function saveExamen()
    {
        $numero_examen = 1;
        $id_theme = $this->getRequest()->get("id_theme", Theme::ID_THEME_EXAMENS_BLANCS);
        $nom = $this->getRequest()->get("nom", "");

        $examen_model = new \APP\Model\Examen();
        $examens = $examen_model->getExamensByIdTheme($id_theme);
        $numero_examen += $examens->count();

        $examen = new Examen();
        $examen
            ->setNumero($numero_examen)
            ->setIdTheme($id_theme)
            ->setNom($nom)
            ->save();

        if ($id_theme > 0) {
            header('Location: /theme/afficher?id=' . $id_theme);
        } else {
            header('Location: /theme/listerExamensBlanc');
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

    function publier()
    {
        $id_examen = $this->getRequest()->get("id_examen", "");

        $examen_model = new \APP\Model\Examen();
        /** @var Examen $examen */
        $examen = $examen_model->get($id_examen);

        $examen->setIsPublished(true);
        $examen->save();
    }

    function supprimer()
    {
        $id_examen = $this->getRequest()->get("id_examen", "");

        $examen_model = new \APP\Model\Examen();
        /** @var Examen $examen */
        $examen = $examen_model->get($id_examen);

        $examen->setIsDeleted(true);
        $examen->save();

        $returned_json = array(
            "is_deleted" => true
        );

        return json_encode($returned_json);
    }
}