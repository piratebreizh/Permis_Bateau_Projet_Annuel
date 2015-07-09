<?php

namespace APP\Module\Index;

use APP\Model\Examen as ExamenModel;
use \APP\Module\Index\View as ViewPath;

class Controller extends \FSF\Controller
{
    public function home()
    {
        $examenModel = new ExamenModel();
        $examensIncomplets = $examenModel->getExamensIncomplets();
        $examensCompletsAValider = $examenModel->getExamensCompletsAValider();

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'home.phtml')
            ->setParam("examensIncomplets", $examensIncomplets)
            ->setParam("examensCompletsAValider", $examensCompletsAValider)
            ;

        return $this->getView()
            ->setParam('currentView', $currentView);
    }
}