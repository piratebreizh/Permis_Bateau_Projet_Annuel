<?php

namespace APP\Module\Question;

use APP\Entity\Question as Question;
use APP\Model\Question as QuestionModel;
use APP\Module\Image\Generator as ImageGenerator;
use APP\Module\Question\View as ViewPath;

class Controller extends \FSF\Controller
{
    public function afficher()
    {
        $id = $this->getRequest()->get('id', 0);

        $model = new QuestionModel();
        /** @var \APP\Entity\Question $question */
        $question = $model->get($id);

        $modelImage = new \APP\Model\Image();
        $image = $modelImage->get($question->getIdImage());

        $examenModel = new \APP\Model\Examen();
        $examen = $examenModel->get($question->getIdExamen());

        $themeModel = new \APP\Model\Theme();
        $theme = $themeModel->get($examen->getIdTheme());

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath().'affichage.phtml')
            ->setParam("question", $question)
            ->setParam("image", $image)
            ->setParam("examen", $examen)
            ->setParam("theme", $theme);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    public function creer()
    {
        $id_examen = $this->getRequest()->get("id_examen", "");

        $examenModel = new \APP\Model\Examen();
        $examen = $examenModel->get($id_examen);

        $themeModel = new \APP\Model\Theme();
        $theme = $themeModel->get($examen->getIdTheme());

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'creation.phtml');
        $currentView->setParam("id_examen", $id_examen);
        $currentView->setParam("examen", $examen);
        $currentView->setParam("theme", $theme);
        $currentView->setParam("question", new Question());

        return $this->getView()
            ->setParam("js", array("question/creation"))
            ->setParam('currentView', $currentView);
    }

    public function saveQuestion()
    {
        $id_question = intval($this->getRequest()->get("id_question", 0));
        $id_examen = $this->getRequest()->get("id_examen", "");
        $enonce_question = $this->getRequest()->get("question_enonce", "");
        $enonce_A = $this->getRequest()->get("enonce_A", "");
        $enonce_B = $this->getRequest()->get("enonce_B", "");
        $enonce_C = $this->getRequest()->get("enonce_C", "");
        $enonce_D = $this->getRequest()->get("enonce_D", "");
        $is_correct_A = $this->getRequest()->get("is_correct_A", false);
        $is_correct_B = $this->getRequest()->get("is_correct_B", false);
        $is_correct_C = $this->getRequest()->get("is_correct_C", false);
        $is_correct_D = $this->getRequest()->get("is_correct_D", false);

        $question_model = new QuestionModel();

        $question = new Question();

        //In case of update
        if($id_question != 0) {
            $question = $question_model->get($id_question);
        }else{
            //Numeration for new Question
            $numero_question = 1;
            $questions = $question_model->getQuestionsByIdExamen($id_examen);
            $numero_question += $questions->count();

            $question->setNumeroQuestion($numero_question);
        }

        $question
            ->setIdExamen($id_examen)
            ->setEnonceQuestion($enonce_question)
            ->setEnonceA($enonce_A)
            ->setEnonceB($enonce_B)
            ->setEnonceC($enonce_C)
            ->setEnonceD($enonce_D)
            ->setIsCorrectA($is_correct_A)
            ->setIsCorrectB($is_correct_B)
            ->setIsCorrectC($is_correct_C)
            ->setIsCorrectD($is_correct_D);

        // Save the image
        $image_generator = new ImageGenerator();

        if ($image_generator->saveImage($_FILES['image'])) {
            $image_generator->resizeImage();
            $question->setIdImage($image_generator->image->getIdImage());
        }

        // Save the question !
        $question->save();

        $add_an_other = $this->getRequest()->get("add_an_other", 0);

        if($add_an_other){
            header('Location: /question/creer?id_examen=' . $id_examen);
        }else{
            header('Location: /examen/afficher?id=' . $id_examen);
        }
    }

    function supprimer()
    {
        $returned_json = array(
            "is_deleted" => false
        );

        $id_question = $this->getRequest()->get("id_question", "");

        if($id_question != "") {
            $model = new QuestionModel();
            /** @var \APP\Entity\Question $question */
            $question = $model->get($id_question);

            $question->setisDeleted(true);
            $question->save();

            $model->updateNumerotation($question->getIdExamen(), $question->getNumeroQuestion());

            $returned_json = array(
                "is_deleted" => true,
            );
        }

        return json_encode($returned_json);
    }

    public function modifier()
    {
        $id_question = $this->getRequest()->get('id', 0);

        $model = new QuestionModel();
        /** @var \APP\Entity\Question $question */
        $question = $model->get($id_question);

        $examenModel = new \APP\Model\Examen();
        $examen = $examenModel->get($question->getIdExamen());

        $themeModel = new \APP\Model\Theme();
        $theme = $themeModel->get($examen->getIdTheme());

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath().'creation.phtml')
            ->setParam("question", $question)
            ->setParam("id_examen", $question->getIdExamen())
            ->setParam("examen", $examen)
            ->setParam("theme", $theme);


        return $this->getView()
            ->setParam("js", array("question/creation"))
            ->setParam('currentView', $currentView);
    }
}