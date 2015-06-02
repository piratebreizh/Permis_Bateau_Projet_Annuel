<?php

namespace APP\Module\WebService;

use APP\Model\Theme as ThemeModel;
use APP\Model\Examen as ExamenModel;
use APP\Model\Question as QuestionModel;
use FSF\Helper\Date;

class Controller extends \FSF\Controller
{
    public function getMaj()
    {
        $date = $this->getRequest()->get("date");
        $date = Date::blockToFr($date);

        $json_array = array(
            "themes_nouveaux" => array(),
            "themes_modifies" => array(),
            "examens_nouveaux" => array(),
            "examens_modifies" => array(),
            "questions_nouvelles" => array(),
            "questions_modifiees" => array(),
            "themes_supprimes" => array(),
            "examens_supprimes" => array(),
            "questions_supprimees" => array(),
        );

        if($date != "") {
            // Themes
            $theme_model = new ThemeModel();
            $themes_modifies = $theme_model->getUpdatedThemes($date);
            foreach ($themes_modifies as $theme_modif) {
                /** @var \APP\Entity\Theme $theme_modif */
                $json_array['themes_modifies'][] = $theme_modif->toArray();
            }
            $themes_nouveaux = $theme_model->getNewThemes($date);
            foreach ($themes_nouveaux as $theme_nouveau) {
                /** @var \APP\Entity\Theme $theme_nouveau */
                $json_array['themes_nouveaux'][] = $theme_nouveau->toArray();
            }
            $themes_supprimes = $theme_model->getDeletedThemes($date);
            foreach ($themes_supprimes as $theme_suppr) {
                /** @var \APP\Entity\Theme $theme_suppr */
                $json_array['themes_supprimes'][] = $theme_suppr->getIdTheme();
            }

            // Examens
            $examen_model = new ExamenModel();
            $examens_modifies = $examen_model->getUpdatedExamens($date);
            foreach ($examens_modifies as $examen_modif) {
                /** @var \APP\Entity\Examen $examen_modif */
                $json_array['examens_modifies'][] = $examen_modif->toArray();
            }
            $examens_nouveaux = $examen_model->getNewExamens($date);
            foreach ($examens_nouveaux as $examen_nouveau) {
                /** @var \APP\Entity\Examen $examen_nouveau */
                $json_array['examens_nouveaux'][] = $examen_nouveau->toArray();
            }
            $examens_supprimes = $examen_model->getDeletedExamens($date);
            foreach ($examens_supprimes as $examen_suppr) {
                /** @var \APP\Entity\Examen $examen_suppr */
                $json_array['examens_supprimes'][] = $examen_suppr->getIdExamen();
            }

            // Questions
            $question_model = new QuestionModel();
            $questions_modifies = $question_model->getUpdatedQuestions($date);
            foreach ($questions_modifies as $question_modif) {
                /** @var \APP\Entity\Question $question_modif */
                $json_array['questions_modifies'][] = $question_modif->toArray();
            }
            $questions_nouveaux = $question_model->getNewQuestions($date);
            foreach ($questions_nouveaux as $question_nouveau) {
                /** @var \APP\Entity\Question $question_nouveau */
                $json_array['questions_nouveaux'][] = $question_nouveau->toArray();
            }
            $questions_supprimes = $question_model->getDeletedQuestions($date);
            foreach ($questions_supprimes as $question_suppr) {
                /** @var \APP\Entity\Question $question_suppr */
                $json_array['questions_supprimes'][] = $question_suppr->getIdQuestion();
            }

        }

        echo json_encode($json_array);
    }
}