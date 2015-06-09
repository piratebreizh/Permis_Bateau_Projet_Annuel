<?php

namespace APP\Module\Cours;

use APP\Entity\Cours;
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

    function save()
    {
        $nom = $this->getRequest()->get("nom");
        $id_theme = $this->getRequest()->get("id_theme");

        $cours = new Cours();
        $cours->setNom($nom);
        $cours->setIdTheme($id_theme);

        // Save the image
        $target_folder = ROOT . "/public/datas/cours/";
        $cours_file = $_FILES['cours'];

        $target_file = $target_folder . $cours->getNomPdf();
        $fileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));
        $uploadOk = true;
        // Allow certain file formats
        if ($fileType != "pdf"
        ) {
            $uploadOk = false;
        }
        if ($uploadOk && move_uploaded_file($cours_file["tmp_name"], $target_file)) {
            $cours->save();
        }

        header('Location: /theme/afficher?id=' . $id_theme);
    }

    function afficher()
    {
        $id_cours = $this->getRequest()->get("id");

        $modelCours = new \APP\Model\Cours();
        /** @var Cours $cours */
        $cours = $modelCours->get($id_cours);

        if (!is_null($cours)) {
            $cours_path = ROOT."/public/datas/cours/" . $cours->getNomPdf();
        }

        if (file_exists($cours_path)) {
            header('Content-Description: File Transfer');
            header('Content-Type: application/octet-stream');
            header('Content-Disposition: attachment; filename='.basename($cours_path));
            header('Expires: 0');
            header('Cache-Control: must-revalidate');
            header('Pragma: public');
            header('Content-Length: ' . filesize($cours_path));
            readfile($cours_path);
            exit;
        }
    }
}