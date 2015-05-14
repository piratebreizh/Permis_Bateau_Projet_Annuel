<?php

namespace APP\Module\Theme;
use APP\Module\Theme\View as ViewPath;

class Controller extends \FSF\Controller
{
    function afficher()
    {
        $id_theme = $this->getRequest()->get('id');

        $themeModel = new \APP\Model\Theme();
        $theme = $themeModel->get($id_theme);

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'affichage.phtml');
        $currentView->setParam("theme", $theme);
        echo $this->getView()
            ->setParam('currentView', $currentView)
            ->render();
    }
}