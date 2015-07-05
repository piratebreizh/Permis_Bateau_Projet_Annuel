<?php
namespace FSF\Routing\Route;

use FSF\Module\User\Controller;
use FSF\Routing\Route;

class User extends Route
{

    /**
     * Must return true if URI can be handled by route
     * @return bool
     */
    public function canHandle()
    {
        $request = $this->getRequest()->getRequestUri();
        if (preg_match("~^/user/(.+)~", $request, $matches)) {
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
}