<?php
namespace APP\Module\Image;

use APP\Entity\Image;
use FSF\Helper\FileSystem;

class Generator
{
    const SD = "SD";
    const HD = "HD";
    const UHD = "UHD";

    public static $RESOLUTION_SIZE = array(
        self::SD  => 100,
        self::HD  => 200,
        self::UHD => 300,
    );

    /** @var  Image */
    public $image;

    /**
     * image resize function
     * @param string $file               file name to resize
     * @param int    $width              new image width
     * @param int    $height             new image height
     * @param string $output             name of the new file (include path if needed)
     * @param bool   $delete_original    if true the original image will be deleted
     * @param bool   $use_linux_commands if set to true will use "rm" to delete the image, if false will use PHP unlink
     * @param int    $quality            enter 1-100 (100 is best quality) default is 100
     * @return bool
     */
    public static function resizeImageFile(
        $file,
        $output,
        $width,
        $height,
        $delete_original = false,
        $use_linux_commands = false,
        $quality = 100
    ) {

        if ($height <= 0 && $width <= 0) {
            return false;
        }
        if ($file === null) {
            return false;
        }
        if ($output === "") {
            $output = $file;
        }

        # Setting defaults and meta
        $info = getimagesize($file);
        $image = '';
        $final_width = 0;
        $final_height = 0;
        list($width_old, $height_old) = $info;
        $cropHeight = $cropWidth = 0;

        # Calculating proportionality
        $ratio_orig = $width_old / $height_old;

        # This is the resizing/resampling/transparency-preserving magic
        $image_resized = imagecreatetruecolor($width, $height);
        imagefill($image_resized, 0, 0, imagecolorallocate($image_resized, 255, 255, 255));

        if ($width / $height > $ratio_orig) {
            $width = $height * $ratio_orig;
        } else {
            $height = $width / $ratio_orig;
        }

        # Loading image to memory according to type
        switch ($info[2]) {
            case IMAGETYPE_JPEG:
                $image = imagecreatefromjpeg($file);
                break;
            case IMAGETYPE_GIF:
                $image = imagecreatefromgif($file);
                break;
            case IMAGETYPE_PNG:
                $image = imagecreatefrompng($file);
                break;
            default:
                return false;
        }

        // Si on est en portrait (w > h)
        if ($width > $height) {
            $new_x = 0;
            $new_y = ($width - $height) / 2;
        } else {
            $new_x = ($height - $width) / 2;
            $new_y = 0;
        }

        imagecopyresampled($image_resized, $image, $new_x, $new_y, 0, 0, $width, $height, $width_old, $height_old);


        # Taking care of original, if needed
        if ($delete_original) {
            if ($use_linux_commands) {
                exec('rm ' . $file);
            } else {
                @unlink($file);
            }
        }

        imagejpeg($image_resized, $output, $quality);

        return true;
    }

    public function saveImage($image_file)
    {
        if (!$this->createDirectoriesTreeForImages()) {
            return false;
        }

        $this->image = new Image();
        $this->image->setNomImage(basename($image_file['name']));

        $target_file = self::getImagesDirectory() . $this->image->getNomImage();
        $imageFileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));
        $uploadOk = true;
        // Allow certain file formats
        if ($imageFileType != "jpg"
            && $imageFileType != "png"
            && $imageFileType != "jpeg"
            && $imageFileType != "gif"
        ) {
            $uploadOk = false;
        }
        if ($uploadOk && move_uploaded_file($image_file["tmp_name"], $target_file)) {
            $this->image->save();

            return true;
        } else {
            return false;
        }
    }

    public static function getImagesDirectory()
    {
        return PUBLIC_DIR . "datas/images/";
    }

    public function resizeImage()
    {
        $file = self::getImagesDirectory() . $this->image->getNomImage();

        foreach(self::$RESOLUTION_SIZE as $res => $size){
            $fileOutput = self::getImagesDirectory() . $res . '/' . $this->image->getNomImage();
            $fileOutput = substr($fileOutput, 0, strrpos($fileOutput, '.')) . '.jpeg';
            $this->resizeImageFile($file, $fileOutput, $size, $size);
        }
    }

    private function createDirectoriesTreeForImages()
    {
        $directories = str_replace(PUBLIC_DIR, "", self::getImagesDirectory());

        return FileSystem::createDirectoriesTree(PUBLIC_DIR, $directories . '/' . self::SD)
               &&
               FileSystem::createDirectoriesTree(PUBLIC_DIR, $directories . '/' . self::HD)
               &&
               FileSystem::createDirectoriesTree(PUBLIC_DIR, $directories . '/' . self::UHD);
    }
}