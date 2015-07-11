<?php
namespace FSF\Module\Error;

use \FSF\Module\Error\View as ViewPath;
use FSF\Response\Header\Status;

class Controller extends \FSF\Controller
{

    public function error404()
    {
        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . '404.phtml');

        $this->getResponse()->addHeader(new Status(Status::VALUE_NOT_FOUND));

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

}