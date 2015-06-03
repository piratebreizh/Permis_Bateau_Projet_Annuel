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

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'affichage.phtml');
        $currentView->setParam("theme", $theme);
        $currentView->setParam("examens", $examens);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    function lister()
    {
        $themeModel = new \APP\Model\Theme();
        $themes = $themeModel->getThemes();

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'liste.phtml');
        $currentView->setParam("themes", $themes);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    function listerExamensBlanc()
    {
        $examenModel = new \APP\Model\Examen();
        $examens_blancs = $examenModel->getExamensBlancs();

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'listerExamensBlanc.phtml');
        $currentView->setParam("examens_blancs", $examens_blancs);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    function creer()
    {
        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'creation.phtml');

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    function saveTheme()
    {
        $numero_theme = $this->getRequest()->get("numero_theme", "");
        $nom = $this->getRequest()->get("nom", "");

        $theme = new Theme();
        $theme
            ->setNumero($numero_theme)
            ->setNom($nom)
            ->save();

        return ('<a href="/theme/lister" >Retour à la liste des thèmes</a>');
    }
}