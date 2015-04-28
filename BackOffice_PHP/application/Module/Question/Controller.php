<?php

namespace APP\Module\Question;

use APP\Model\Question as QuestionModel;
use APP\Entity\Question as Question;

class Controller extends \FSF\Controller
{
    public function afficher()
    {
        $id = $this->getRequest()->get('id', 0);
        echo 'afficher Question : ' . $id . '<br/>';

        $model = new QuestionModel();
        /** @var \APP\Entity\Question $question */
        $question = $model->get($id);

        echo $question->getEnonceQuestion();
        echo "<li>" . $question->getEnonceA();
        echo "<li>" . $question->getEnonceB();
        echo "<li>" . $question->getEnonceC();
        echo "<li>" . $question->getEnonceD();

        $question->setEnonceC("CCC")->save();
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