<?php

namespace APP\Module\Examen;
use APP\Module\Examen\View as ViewPath;

class Controller  extends \FSF\Controller
{
    function afficher()
    {
        $id_examen = $this->getRequest()->get('id');

        $examenModel = new \APP\Model\Examen();
        $examen = $examenModel->get($id_examen);

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'affichage.phtml');
        $currentView->setParam("examen", $examen);
        echo $this->getView()
            ->setParam('currentView', $currentView)
            ->render();
    }
}