<?php

namespace APP\Module\Theme;

use APP\Entity\Theme;
use APP\Module\Theme\View as ViewPath;

class Controller extends \FSF\Controller
{
    function afficher()
    {
        $id_theme = $this->getRequest()->get('id');

        $themeModel = new \APP\Model\Theme();
        $theme = $themeModel->get($id_theme);

        $examenModel = new \APP\Model\Examen();
        $examens = $examenModel->getExamensByIdTheme($id_theme);

        $coursModel = new \APP\Model\Cours();
        $cours = $coursModel->getCoursByIdTheme($id_theme);

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'affichage.phtml');
        $currentView->setParam("theme", $theme);
        $currentView->setParam("examens", $examens);
        $currentView->setParam("cours", $cours);

        return $this->getView()
            ->setParam('currentView', $currentView)
            ->setParam("js", array("theme/affichage"));
    }

    function lister()
    {
        $themeModel = new \APP\Model\Theme();
        $themes = $themeModel->getThemes();

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'liste.phtml');
        $currentView->setParam("themes", $themes);

        return $this->getView()
            ->setParam('currentView', $currentView)
            ->setParam("js", array("theme/listing"));
    }

    function listerExamensBlanc()
    {
        $examenModel = new \APP\Model\Examen();
        $examens_blancs = $examenModel->getExamensBlancs();

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'listerExamensBlanc.phtml');
        $currentView->setParam("examens_blancs", $examens_blancs);

        return $this->getView()
            ->setParam('currentView', $currentView)
            ->setParam("js", array("theme/affichage"));
    }

    function saveTheme()
    {
        $nom = $this->getRequest()->get("nom", "");

        $numero_theme = 1;
        $theme_model = new \APP\Model\Theme();
        $allThemes = $theme_model->getThemes();
        $numero_theme += $allThemes->count();

        $theme = new Theme();
        $theme
            ->setNumero($numero_theme)
            ->setNom($nom)
            ->save();

        header('Location: /theme/lister');
    }

    function publier()
    {
        $id_theme = $this->getRequest()->get("id_theme", "");

        $theme_model = new \APP\Model\Theme();
        /** @var Theme $theme */
        $theme = $theme_model->get($id_theme);

        $theme->setIsPublished(true);
        $theme->save();
    }

    function supprimer()
    {
        $id_theme = $this->getRequest()->get("id_theme", "");

        $theme_model = new \APP\Model\Theme();
        /** @var Theme $theme */
        $theme = $theme_model->get($id_theme);

        $theme->setIsDeleted(true);
        $theme->save();

        $theme_model->updateNumerotation($theme->getNumero());

        $returned_json = array(
            "is_deleted" => true
        );

        return json_encode($returned_json);
    }
}