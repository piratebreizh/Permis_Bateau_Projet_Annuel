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
        $examensCompletsAPublier = $examenModel->getExamensCompletsAValider();

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'home.phtml')
            ->setParam("examensIncomplets", $examensIncomplets)
            ->setParam("examensCompletsAPublier", $examensCompletsAPublier)
            ;

        return $this->getView()
            ->setParam('js', array('index/home'))
            ->setParam('css', array('index/home'))
            ->setParam('currentView', $currentView);
    }
}