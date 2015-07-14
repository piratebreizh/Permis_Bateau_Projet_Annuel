<?php

namespace APP\Module\WebService;

use APP\Entity\Cours;
use APP\Model\Examen as ExamenModel;
use APP\Model\Question as QuestionModel;
use APP\Model\Theme as ThemeModel;
use APP\Module\Image\Generator as ImageGenerator;
use FSF\Helper\Date;

class Controller extends \FSF\Controller
{
    public function getMaj()
    {
        ini_set('precision', 14);

        $date = $this->getRequest()->get("date");
        $date = Date::blockToFr($date);

        $json_array = array(
            "date_update"          => date('dmYHis').' ',
            "is_empty"             => true,
            "themes_nouveaux"      => array(),
            "themes_modifies"      => array(),
            "examens_nouveaux"     => array(),
            "examens_modifies"     => array(),
            "questions_nouvelles"  => array(),
            "questions_modifiees"  => array(),
            "themes_supprimes"     => array(),
            "examens_supprimes"    => array(),
            "questions_supprimees" => array(),
            "cours_nouveaux"       => array(),
            "cours_supprimes"      => array(),
        );

        if ($date != "") {
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
                $json_array['questions_modifiees'][] = $question_modif->toArray();
            }
            $questions_nouveaux = $question_model->getNewQuestions($date);
            foreach ($questions_nouveaux as $question_nouveau) {
                /** @var \APP\Entity\Question $question_nouveau */
                $json_array['questions_nouvelles'][] = $question_nouveau->toArray();
            }
            $questions_supprimes = $question_model->getDeletedQuestions($date);
            foreach ($questions_supprimes as $question_suppr) {
                /** @var \APP\Entity\Question $question_suppr */
                $json_array['questions_supprimees'][] = $question_suppr->getId();
            }

            // Cours
            $cours_model = new \APP\Model\Cours();
            $cours_nouveaux = $cours_model->getNewCours($date);
            foreach ($cours_nouveaux as $cours_nouveau) {
                /** @var \APP\Entity\Cours $cours_nouveau */
                $json_array['cours_nouveaux'][] = $cours_nouveau->toArray();
            }
            $cours_supprimes = $cours_model->getDeletedCours($date);
            foreach ($cours_supprimes as $cours_suppr) {
                /** @var \APP\Entity\Cours $cours_suppr */
                $json_array['cours_supprimes'][] = $cours_suppr->getIdCours();
            }
        }

        // Test if JSON is not empty
        $fields = array(
            "themes_nouveaux",
            "themes_modifies",
            "examens_nouveaux",
            "examens_modifies",
            "questions_nouvelles",
            "questions_modifiees",
            "themes_supprimes",
            "examens_supprimes",
            "questions_supprimees",
            "cours_nouveaux",
            "cours_supprimes",
        );
        foreach ($fields as $field) {
            if (!empty($json_array[$field])) {
                $json_array["is_empty"] = false;
                break;
            }
        }

        return json_encode($json_array, JSON_UNESCAPED_UNICODE | JSON_NUMERIC_CHECK);
    }

    function getImage()
    {
        $id_image = $this->getRequest()->get("id");
        $resolution = $this->getRequest()->get("resolution", ImageGenerator::HD);

        if (!array_key_exists($resolution, ImageGenerator::$RESOLUTION_SIZE)) {
            $resolution = ImageGenerator::HD;
        }

        $modelImage = new \APP\Model\Image();
        $image = $modelImage->get($id_image);

        if (!is_null($image)) {
            $image_path = ImageGenerator::getImagesDirectory() . $resolution . '/' . $image->getNomImage();
            $image_path = substr($image_path, 0, strrpos($image_path, '.')) . '.png';
        }
        if (!file_exists($image_path)) {
            $image_path = substr($image_path, 0, strrpos($image_path, '.')) . '.jpeg';
        }

        if (file_exists($image_path)) {
            header('Content-Description: File Transfer');
            header('Content-Type: application/octet-stream');
            header('Content-Disposition: attachment; filename=' . basename($image_path));
            header('Expires: 0');
            header('Cache-Control: must-revalidate');
            header('Pragma: public');
            header('Content-Length: ' . filesize($image_path));
            readfile($image_path);
            exit;
        }
    }

    function getCours()
    {
        $id_cours = $this->getRequest()->get("id");

        $modelCours = new \APP\Model\Cours();
        /** @var Cours $cours */
        $cours = $modelCours->get($id_cours);

        if (!is_null($cours)) {
            $cours_path = ROOT . "/public/datas/cours/" . $cours->getNomPdf();
        }

        if (file_exists($cours_path)) {
            header('Content-Description: File Transfer');
            header('Content-Type: application/octet-stream');
            header('Content-Disposition: attachment; filename=' . basename($cours_path));
            header('Expires: 0');
            header('Cache-Control: must-revalidate');
            header('Pragma: public');
            header('Content-Length: ' . filesize($cours_path));
            readfile($cours_path);
            exit;
        }
    }
}