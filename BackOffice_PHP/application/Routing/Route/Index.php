<?php

namespace APP\Routing\Route;

use APP\Module\Index\Controller;
use FSF\Routing\Route;

class Index extends Route
{

    /**
     * Must return true if URI can be handled by route
     * @return bool
     */
    public function canHandle()
    {
        $request = $this->getRequest()->getRequestUri();

        if ($request == '/') {
            $this->setAction('home');
            if (method_exists($this->getController(), $this->getAction()))
                return true;
        }
        return false;
    }

    /**
     * @return string
     */
    public function getController()
    {
        if ($this->controller == null) {
            $this->controller = new Controller($this->getRequest());
        }

        return $this->controller;
    }
}