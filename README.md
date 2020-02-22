# Hwahae, android app

Repository for the Hwahae Android App

![GitHub code size in bytes](https://img.shields.io/badge/Min%20API%20Level-21-green)
![GitHub code size in bytes](https://img.shields.io/badge/Max%20API%20Level-29-orange)
![GitHub code size in bytes](https://img.shields.io/github/repo-size/lmy4080/Hwahae?color=blue)

## Screenshots

<img src="https://user-images.githubusercontent.com/42701193/74667206-3dd63d80-51e6-11ea-95b0-e43d7a0af33b.png" width="240"/> <img hspace="10" src="https://user-images.githubusercontent.com/42701193/74667217-43cc1e80-51e6-11ea-90a0-723d0eeb3622.png" width="240"/> <img src="https://user-images.githubusercontent.com/42701193/74667220-4595e200-51e6-11ea-9b8b-caa00017d200.png" width="240"/><br><br>
<img src="https://user-images.githubusercontent.com/42701193/74667222-462e7880-51e6-11ea-8858-984c4e414b44.png" width="240"/> <img hspace="10" src="https://user-images.githubusercontent.com/42701193/74667226-475fa580-51e6-11ea-90a9-a164c2f70d24.png" width="240"/> <img src="https://user-images.githubusercontent.com/42701193/74667227-47f83c00-51e6-11ea-96a8-479a3c2f6887.png" width="240"/><br><br>
<img src="https://user-images.githubusercontent.com/42701193/74667231-49c1ff80-51e6-11ea-8efd-a3ffedb8be55.png" width="240"/>

## Demo

 - https://youtu.be/eKR-8eWyOU8

## About

버드뷰(화해) 블라인드 채용 - 서버, 앱 개발 챌린지 과제

## App Features

|   **Feature**          | **Description**                                                   | **Status**         |
|------------------------|-------------------------------------------------------------------|--------------------|
|   IndexViewActivity    | 카테고리별 상품 조회, "모든 피부 타입(Default)", Oily", "Dry", "Sensitive"| :heavy_check_mark: |
|                        | 하단 스크롤시 상품 20개씩 페이징                                         | :heavy_check_mark: |
|                        | 스크롤시 헤더 목록 표시 및 사라짐                                         | :heavy_check_mark: |
|                        | 사용자 키워드를 통한 상품 검색                                           | :heavy_check_mark: |
|                        | 상품 클릭 시 상세 정보 페이지로 이동                                      | :heavy_check_mark: |
|   DetailViewActivity   | 상품 상세 정보 표시                                                   | :heavy_check_mark: |
|                        | 페이지 표시 중 애니메이션 효과                                           | :heavy_check_mark: |
|                        | 페이지 마지막 스크롤 위치 저장 및 다음 페이지 표시 시 복원                     | :heavy_check_mark: |
|                        | 상품 클릭 시 상세 정보 페이지로 이동                                      | :heavy_check_mark: |


## Project Folder Structure

```
src/
├── datasoruce
│   ├── local
│   └── remote
│       ├── HwahaeDataSource.kt
│       ├── HwahaeDataSourceFactory.kt
│       ├── api
│       │   └── HwahaeWebService.kt
│       ├── model
│       │   └── ServerResponseModel.kt
│       └── status
│           └── NetworkStatus.kt
├── repository
│   └── HwahaeRepository.kt
├── ui
│   ├── adpaters
│   │   ├── IndexViewAdapter.kt
│   │   └── IndexViewAdapterListener.kt
│   ├── status
│   │   ├── DetailViewStatus.kt
│   │   └── IndexViewStatus.kt
│   ├── utils
│   │   ├── AnimationHelper.kt
│   │   └── FormatPlainToPrice.kt
│   └── views
│       ├── DetailViewDialog.kt
│       ├── IndexViewActivity.kt
│       ├── IndexViewCustomSpinner.kt
│       └── SpinnerData.kt
└── viewmodel
    ├── DetailViewModel.kt
    └── IndexViewModel.kt
```

## Development
 - 사용 언어: Kotlin
 - MVVM(Model-View-ViewModel Pattern)
 - 활용 기술 스택
   - CoordinateLayout, AppbarLayout, CollapsingToolbar, Toolbar, RecyclerView
   - ViewModel and LiveData
   - Paging Library
   - Coroutine
   - Retrofit
   - Glide
   - SpringAnimation
   - Espresso
   - Mockito

## API Response, /GET

- Base Url: https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/

- Parameter: 'skin_type', 
  - Value: 'oily(default)'
  - Ex) Response, https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?skin_type=oily
  
  ```
  {
   "statusCode": 200,
   "body": [
     {
       "id": 536,
       "price": "19650",
       "oily_score": 100,
       "thumbnail_image": "https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com/2020-birdview/thumbnail/ef792a79-435c-44eb-b9dc-285750ae1517.jpg",
       "title": "플라멜엠디 밀크러스트필 마일드 워시오프 앰플 5ml x 2개"
     },
     ...
   "scanned_count": 20
  } 
  ```
 
- Parameter: 'skin_type', 
  - Value: 'dry'
  - Ex) Response, https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?skin_type=dry

  ```
  {
   "statusCode": 200,
   "body": [
     {
      "dry_score": 100,
      "id": 873,
      "price": "95410",
      "thumbnail_image": "https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com/2020-birdview/thumbnail/f5177afd-60b4-4cae-ac33-8560a2742c12.jpg",
      "title": "피토메르 아빼장 마스크 150ml"
     },
     ...
   "scanned_count": 20
  } 
  ```
  
- Parameter: 'skin_type', 
  - Value: 'sensitive'
  - Ex) Response, https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?skin_type=sensitive
  
  ```
  {
   "statusCode": 200,
   "body": [
     {
      "sensitive_score": 100,
      "id": 828,
      "price": "3560",
      "thumbnail_image": "https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com/2020-birdview/thumbnail/27feebd6-d863-48e6-85b8-336778f3c455.jpg",
      "title": "엔오에이치제이 코리안 에스테틱 마스크 포어버블"
     },
     ...
   "scanned_count": 20
  } 
  ```
  
- Parameter: 'page', 
  - Value: '1'
  - Ex) Response, https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?skin_type=oily&page=1
  
  ```
  {
   "statusCode": 200,
   "body": [
     {
       "id": 536,
       "price": "19650",
       "oily_score": 100,
       "thumbnail_image": "https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com/2020-birdview/thumbnail/ef792a79-435c-44eb-b9dc-285750ae1517.jpg",
       "title": "플라멜엠디 밀크러스트필 마일드 워시오프 앰플 5ml x 2개"
     },
     ...
   "scanned_count": 20
  } 
  ```

- Parameter: 'search', 
  - Value: '150ml'
  - Ex) Response, https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?search=150ml
  
  ```
  {
   "statusCode": 200,
   "body": [
     {
      "id": 710,
      "price": "9600",
      "oily_score": 94,
      "thumbnail_image": "https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com/2020-birdview/thumbnail/7542e592-878e-49c4-9f61-65ab23b9ac72.jpg",
      "title": "오딧세이 블랙 클렌징 폼 150ml"
     },
     ...
   "scanned_count": 20
  } 
  ```

## Developer

### Android app
- 이민영 ([@Min Young Lee](https://github.com/lmy4080))

