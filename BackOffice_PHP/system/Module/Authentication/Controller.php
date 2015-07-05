<?php
namespace FSF\Module\Authentication;

use FSF\Entity\User;
use FSF\Model\User as UserModel;
use FSF\Module\Authentication\View as ViewPath;

class Controller extends \FSF\Controller
{
    public static $authAdress = "/authentication/index";

    public function index()
    {
        $currentView = new View();
        $currentView->setViewPath(ViewPath::getPath() . 'index.phtml');

        return $this->getView()
            ->setParam("js", array("authentication/index"))
            ->setParam('currentView', $currentView);
    }

    public function login()
    {
        $returned_JSON = array(
            "is_connected" => false,
            "msgError" => "Les identifiants saisis ne sont pas valides",
            "url_redirect" => "/"
        );

        $username = $this->getRequest()->get('username', "");
        $password = $this->getRequest()->get('password', "");

        $userModel = new UserModel();
        /** @var User $user */
        $user = $userModel->getUserByUserName($username);

        if(!is_null($user)){
            $user_pwd = $user->getPassword();
            if ( hash_equals($user_pwd, crypt($password, $user_pwd)) ) {
                $this->getSession()->set("user", $user);
                $returned_JSON['is_connected'] = true;
            }else{
                $returned_JSON['msgError'] = "Mauvais mot de passe";
            }
        }

        return json_encode($returned_JSON);
    }

    public function logout()
    {
        $this->getSession()->destroy();

        header('Location: '.self::$authAdress);
    }
}