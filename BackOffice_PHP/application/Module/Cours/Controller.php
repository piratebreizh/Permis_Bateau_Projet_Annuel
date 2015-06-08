<?php

namespace APP\Module\Cours;

use APP\Module\Cours\View as ViewPath;

class Controller extends \FSF\Controller
{
    function creer()
    {
        $id_theme = $this->getRequest()->get("id_theme", "");

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'creation.phtml');
        $currentView->setParam("id_theme", $id_theme);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }
}