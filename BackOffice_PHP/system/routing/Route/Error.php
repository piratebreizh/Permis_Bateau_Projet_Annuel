<?php
namespace FSF\Routing\Route;

use FSF\Module\Error\Controller;
use FSF\Routing\Route;

class Error extends Route
{

    const ERROR_404 = "error404";

    /**
     * Must return true if URI can be handled by route
     * @return bool
     */
    public function canHandle()
    {
        $request = $this->getRequest()->getRequestUri();
        if (preg_match("~^/erreur/(.+)~", $request, $matches)) {
            if (isset($matches[1])) {
                $this->setAction("error".$matches[1]);
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