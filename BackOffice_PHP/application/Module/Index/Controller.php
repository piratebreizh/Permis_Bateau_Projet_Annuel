<?php

namespace APP\Module\Index;

use \APP\Module\Index\View as ViewPath;

class Controller extends \FSF\Controller
{
    public function home()
    {
        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'home.phtml');
        echo $this->getView()
            ->setParam('currentView', $currentView)
            ->render();
    }
}