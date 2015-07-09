<?php

namespace APP\Module\Index;

use APP\Model\Examen as ExamenModel;
use \APP\Module\Index\View as ViewPath;

class Controller extends \FSF\Controller
{
    public function home()
    {
        $examenModel = new ExamenModel();
        $examensBlancs = $examenModel->getExamensBlancs();

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'home.phtml')
            ->setParam("examensBlancs", $examensBlancs);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }
}