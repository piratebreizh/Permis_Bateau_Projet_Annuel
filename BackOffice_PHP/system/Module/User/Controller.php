<?php
namespace FSF\Module\User;

use FSF\Entity\User;
use FSF\Model\User as UserModel;
use FSF\Module\User\View as ViewPath;

class Controller extends \FSF\Controller
{
    public function lister()
    {
        $userModel = new UserModel();
        $users = $userModel->getAll();

        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'lister.phtml')
            ->setParam("users", $users);

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    public function creation()
    {
        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'creation.phtml');

        return $this->getView()
            ->setParam('currentView', $currentView);
    }

    public function validateUserName()
    {
        $username = $this->getRequest()->get("username");

        $userModel = new UserModel();
        /** @var User $user */
        $user = $userModel->getUserByUserName($username);

        if(!is_null($user)){
            http_response_code(400);
        }

        return "{}";
    }

    public function save()
    {
        $username = $this->getRequest()->get("username");
        $password = $this->getRequest()->get("password");
        $name = $this->getRequest()->get("name");
        $firstName = $this->getRequest()->get("firstname");

        $user = new User();
        $user->setUserName($username)
            ->setPassword($password)
            ->setName($name)
            ->setFirstName($firstName)
            ->save();

        header('Location: /user/lister');
    }
}