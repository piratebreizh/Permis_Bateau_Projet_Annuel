<?php

namespace APP\Module\Index;

use \APP\Module\Index\View as ViewPath;
use FSF\Entity\User;

class Controller extends \FSF\Controller
{
    public function home()
    {
        /** @var User $user */
        $user = $this->getSession()->get('user');

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'home.phtml')
            ->setParam("user", $user);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }
}