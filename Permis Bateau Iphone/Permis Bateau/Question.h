//
//  Question.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 06/04/2015.
//  Copyright (c) 2015 Alexandre Dubois. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Serie, Theme;

@interface Question : NSManagedObject

@property (nonatomic, retain) NSString * enoncer;
@property (nonatomic, retain) NSString * image;
@property (nonatomic, retain) NSNumber * numero;
@property (nonatomic, retain) NSString * reponseA;
@property (nonatomic, retain) NSString * reponseB;
@property (nonatomic, retain) NSString * reponseC;
@property (nonatomic, retain) NSNumber * reponseCorrectA;
@property (nonatomic, retain) NSNumber * reponseCorrectB;
@property (nonatomic, retain) NSNumber * reponseCorrectC;
@property (nonatomic, retain) NSNumber * reponseCorrectD;
@property (nonatomic, retain) NSString * reponseD;
@property (nonatomic, retain) Theme *theme;
@property (nonatomic, retain) Serie *serie;

@end
