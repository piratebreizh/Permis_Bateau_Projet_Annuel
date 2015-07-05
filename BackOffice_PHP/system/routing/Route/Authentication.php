<?php
namespace FSF\Routing\Route;

use FSF\Module\Authentication\Controller;
use FSF\Routing\Route;

class Authentication extends Route{

    /**
     * Must return true if URI can be handled by route
     * @return bool
     */
    public function canHandle()
    {
        $request = $this->getRequest()->getRequestUri();
        if (preg_match("~^/authentication/(.+)~", $request, $matches)) {
            if (isset($matches[1])) {
                $this->setAction($matches[1]);
            }
            if (method_exists($this->getController(), $this->getAction()))
                return true;
        }
        return false;
    }

    /**
     * @return Controller
     */
    public function getController()
    {
        if ($this->controller == null) {
            $this->controller = new Controller($this->getRequest());
        }

        return $this->controller;
    }

    public function needAuthentication()
    {
        return false;
    }
}