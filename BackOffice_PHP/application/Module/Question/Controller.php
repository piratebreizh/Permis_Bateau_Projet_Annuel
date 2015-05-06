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
        echo $this->getView()
            ->setParam('currentView', $currentView)
            ->render();
    }

    public function creer()
    {
        $question = new Question();
        $question
            ->setNumeroQuestion(4)
            ->setEnonceQuestion("test = 'sef' ?")
            ->setEnonceA("A")
            ->setEnonceB("B")
            ->setEnonceC("C")
            ->setEnonceD("D")
            ->setIsCorrectA(true)
            ->save();
    }

    public function lister()
    {
        $model = new QuestionModel();

        $questions = $model->getAllQuestions();

        foreach ($questions as $question) {
            echo '<p>';
            echo $question->getEnonceQuestion();
            echo "<li>" . $question->getEnonceA();
            echo "<li>" . $question->getEnonceB();
            echo "<li>" . $question->getEnonceC();
            echo "<li>" . $question->getEnonceD();
            echo '</p>';
        }
    }
}